package com.gmv2.pdv.service;

import com.gmv2.pdv.dto.ProductDTO;
import com.gmv2.pdv.dto.ProductInfoDTO;
import com.gmv2.pdv.dto.SaleDTO;
import com.gmv2.pdv.dto.SaleInfoDTO;
import com.gmv2.pdv.entity.ItemSale;
import com.gmv2.pdv.entity.Product;
import com.gmv2.pdv.entity.Sale;
import com.gmv2.pdv.entity.User;
import com.gmv2.pdv.exceptions.InvalideOperationException;
import com.gmv2.pdv.exceptions.NoItemException;
import com.gmv2.pdv.repository.ItemSaleRepository;
import com.gmv2.pdv.repository.ProductRepository;
import com.gmv2.pdv.repository.SaleRepository;
import com.gmv2.pdv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private  final ItemSaleRepository itemSaleRepository;

    public SaleInfoDTO getById(long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(()-> new NoItemException("Venda não encontrada!"));
        return getSaleInfo(sale);
    }

    @Transactional
    public List<SaleInfoDTO> findAll(){
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }
    private SaleInfoDTO getSaleInfo(Sale sale){
        SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
        /*
        saleInfoDTO.setUser();
        saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleInfoDTO.setProducts(getProductInfo(sale.getItems()));

         */
        return SaleInfoDTO.builder()
                .User(sale.getUser().getName())
                .date(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .products(getProductInfo(sale.getItems()))
                .build();
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {

        if (CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }

      /*  return items.stream().map(item -> {
            ProductInfoDTO productInfoDTO = new ProductInfoDTO();
            productInfoDTO.setId(item.getId());
            productInfoDTO.setDescription(item.getProduct().getDescription());
            productInfoDTO.setQuantity(item.getQuantity());
            return productInfoDTO;
        }).collect(Collectors.toList());*/

        return items.stream().map(item -> ProductInfoDTO.builder()
                .id(item.getId())
                .description(item.getProduct().getDescription())
                .quantity(item.getQuantity())
                .build()
        ).collect(Collectors.toList());

    }


    @Transactional
    public long save(SaleDTO sale){

        Optional<User> optional = userRepository.findById(sale.getUserid());
        if (optional.isPresent()){
            User user = optional.get();
            Sale newSale = new Sale();
            newSale.setUser(user);
            newSale.setDate(LocalDate.now());
            List<ItemSale> item = getItemSale(sale.getItems());

            newSale = saleRepository.save(newSale);
            saveItemSave(item, newSale);
            return newSale.getId();
        } else {
            throw new NoItemException("Usuario não encontrado");
        }
    }

    private void saveItemSave(List<ItemSale> items, Sale newSale) {
        for (ItemSale item : items){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemSale(List<ProductDTO> products) {

        if(products.isEmpty()){
            throw new InvalideOperationException("Não e possivel adicionar venda sem itens!");
        }

        return products.stream().map(item ->{
            Product product = productRepository.getReferenceById(item.getProductid());
            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if (product.getQuantity() == 0){
             throw new NoItemException("Produto sem estoque");
            } else if(product.getQuantity() < item.getQuantity()){
                throw new InvalideOperationException(String.format("Quantidade de itens da venda (%s)" + "" +
                        "é maior do que a quantidade disponivel no estoque (%s", item.getQuantity(), product.getQuantity() ));
            }

            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());

    }


}
