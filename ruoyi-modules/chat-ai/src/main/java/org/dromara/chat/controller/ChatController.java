package org.dromara.chat.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SaIgnore
@RestController
@RequestMapping("/ai")
public class ChatController {
    private static final String DEFAULT_PROMPT = "你好，介绍下你自己吧。";

    private final ChatModel DeepSeekChatModel;

    public ChatController (ChatModel chatModel) {
        this.DeepSeekChatModel = chatModel;
    }

    /**
     * 最简单的使用方式，没有任何 LLMs 参数注入。
     *
     * @return String types.
     */
    @GetMapping("/simple/chat")
    public String simpleChat () {
        return DeepSeekChatModel.call(new Prompt(DEFAULT_PROMPT)).getResult().getOutput().getContent();
    }

    /**
     * Stream 流式调用。可以使大模型的输出信息实现打字机效果。
     *
     * @return Flux<String> types.
     */
    @GetMapping("/stream/chat")
    public Flux<ServerSentEvent<String>> streamChat (@RequestParam(value = "message", defaultValue =
        "你是问诊仪助手") String message) {
        Flux<ChatResponse> stream = DeepSeekChatModel.stream(new Prompt(new UserMessage(message)));
        return stream.map(resp -> ServerSentEvent.builder(resp.getResult().getOutput().getContent()).build());
    }
}
