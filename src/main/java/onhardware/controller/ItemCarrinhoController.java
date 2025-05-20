package onhardware.controller;

import onhardware.service.ItemCarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemCarrinho")
public class ItemCarrinhoController {

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;
}
