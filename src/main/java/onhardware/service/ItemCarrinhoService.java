package onhardware.service;

import onhardware.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCarrinhoService {

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

}
