//package controller;
//
//
//import entity.ChatMessage;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//import java.util.ArrayList;
//import repository1.ChatRepository;
////import org.springframework.messaging.simp.SimpMessagingTemplate;
////import org.springframework.beans.factory.annotation.Autowired;
//
//@WebServlet("/chat")
//public class ChatServlet extends HttpServlet {
//
//    private List<ChatMessage> chatMessages = new ArrayList<>();
//    private ChatRepository chatMessageDAO = new ChatRepository();
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate; // Tiêm SimpMessagingTemplate
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setAttribute("chatMessages", chatMessages);
//        request.getRequestDispatcher("/WEB-INF/views/chat.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String content = request.getParameter("content");
//        Long senderId = Long.parseLong(request.getParameter("senderId"));
//        Long receiverId = Long.parseLong(request.getParameter("receiverId"));
//
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setContent(content);
//        chatMessage.setSenderId(senderId);
//        chatMessage.setReceiverId(receiverId);
//        chatMessage.setTimestamp(String.valueOf(System.currentTimeMillis()));
//
//        // Chèn vào cơ sở dữ liệu
//        chatMessageDAO.insertChatMessage(chatMessage);
//
//        // Gửi tin nhắn qua WebSocket
//        messagingTemplate.convertAndSend("/topic/public", chatMessage);
//
//        response.sendRedirect("chat");
//    }
//}
