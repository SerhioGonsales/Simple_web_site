package com.lukushin.simple_web_site.controller;

import com.lukushin.simple_web_site.entity.Message;
import com.lukushin.simple_web_site.entity.User;
import com.lukushin.simple_web_site.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {
    
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Model model){
        List<Message> messages;
        if(filter != null && !filter.isEmpty()){
            messages = messageRepository.findByTagContains(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @RequestParam ("file") MultipartFile file,
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Model model) throws IOException {
        Message message = new Message(text, tag, user);

        if(file!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFilename = UUID.randomUUID().toString();
            String resultFilename = uuidFilename + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }

        messageRepository.save(message);
        Iterable<Message> messageList = messageRepository.findAll();
        model.addAttribute("messages", messageList);
        return "main";
    }
}
