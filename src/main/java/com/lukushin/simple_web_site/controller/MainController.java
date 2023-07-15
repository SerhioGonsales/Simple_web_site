package com.lukushin.simple_web_site.controller;

import com.lukushin.simple_web_site.entity.Message;
import com.lukushin.simple_web_site.repository.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message> messageList = messageRepository.findAll();
        model.put("message", messageList);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag);
        messageRepository.save(message);
        Iterable<Message> messageList = messageRepository.findAll();
        model.put("message", messageList);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        List<Message> list;
        if(filter != null && !filter.isEmpty()){
            list = messageRepository.findByTag(filter);
        } else {
            list = messageRepository.findAll();
        }
        model.put("message", list);
        return "main";
    }
}
