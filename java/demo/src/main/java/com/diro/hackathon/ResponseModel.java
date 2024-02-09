package com.diro.hackathon;

import java.util.ArrayList;
// Parser for the response from the API

// The response from the API is a JSON object with the following structure:
// {
//     "choices": [
//         {
//             "finish_reason": "length",
//             "index": 0,
//             "message": {
//                 "content": "int main() {\n    return 0;\n}",
//                 "role": "assistant"
//             }
//         }
//     ],
//     "created": 1633660000,
//     "id": "cmpl-3X3J3b",
//     "model": "LLaMA_CPP",
//     "object": "text_completion",
//     "usage": {
//         "completion_tokens": 3,
//         "prompt_tokens": 3,
//         "total_tokens": 6
//     }
// }

// The ResponseModel class is a representation of the JSON object returned by the API.
// It contains fields for the choices, created timestamp, id, model, object, and usage.
// The Choice class represents the choices array in the JSON object.
// It contains fields for the finish_reason, index, and message.
class Choice {
    public String finish_reason;
    public int index;
    public Message message;
}

// The Message class represents the message object in the choices array.
// It contains fields for the content and role.
class Message {
    public String content;
    public String role;
}

// The ResponseModel class represents the JSON object returned by the API.
// It contains fields for the choices, created timestamp, id, model, object, and
// usage.
public class ResponseModel {
    public ArrayList<Choice> choices;
    public int created;
    public String id;
    public String model;
    public String object;
    public Usage usage;
}

// The Usage class represents the usage object in the JSON object.
// It contains fields for the completion_tokens, prompt_tokens, and
// total_tokens.
class Usage {
    public int completion_tokens;
    public int prompt_tokens;
    public int total_tokens;
}
