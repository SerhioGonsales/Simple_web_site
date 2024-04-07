package com.lukushin.simple_web_site.controller;

import com.lukushin.simple_web_site.entity.Message;
import com.lukushin.simple_web_site.entity.User;
import com.lukushin.simple_web_site.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public String main(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String filter,
            Model model){
        List<Message> messages;
        if(filter != null && !filter.isEmpty()){
            messages = messageRepository.findByTagContains(filter);
        } else {
            messages = messageRepository.findAll();
        }
        //TODO без этой проверки и добавления user в модель
        // на главной странице отображается кнопка "вход" вместо "выход"
        if(currentUser!=null){
            model.addAttribute("user", currentUser);
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(
            @RequestParam ("file") MultipartFile file,
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model) throws IOException {

        message.setAuthor(user);

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);
//            return "/main";
        } else{
            //TODO даже если нет прикрепленного файла к сообщению создается имя файла
            // и потом на странице отображается "битая" картинка
            saveFile(file, message);
        }
        Iterable<Message> messageList = messageRepository.findAll();
        model.addAttribute("messages", messageList);
        return "main";
    }

    @GetMapping("/user/messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam (required = false) Message message
    ){
        Set<Message> messages = user.getMessages();
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        return "userMessages";
    }

    @PostMapping("/user/messages/{user}")
    public String saveMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam ("id") Message message,
            @RequestParam ("text") String text,
            @RequestParam ("tag") String tag,
            @RequestParam ("file")MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)){
            if(!StringUtils.isEmpty(text)){
                message.setText(text);
            }

            if(!StringUtils.isEmpty(tag)){
                message.setTag(tag);
            }
        }

        saveFile(file, message);
        messageRepository.save(message);
        return "redirect:/userMessages" + user;
    }



    //TODO перенести в userService
    private void saveFile(MultipartFile file, Message message) throws IOException {
        if(file !=null){
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
    }
}
