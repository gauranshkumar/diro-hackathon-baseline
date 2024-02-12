from openai import OpenAI


# Create a client
def create_client(url: str = "http://localhost:7777/v1") -> OpenAI:

    # return OpenAI instance with the base_url
    return OpenAI(
        base_url=url, # "http://<Your api-server IP>:port
        api_key = "sk-no-key-required"
    )


# if the script is run directly
if __name__ == "__main__":

    # create a client
    client = create_client()

    print("==========Welcome to DIRO Bot==========")
    print("Type 'exit' or 'quit' to end the conversation\n")

    messages = []
    while True:

        # get the user input
        message = input("Prompt> ")

        # append the user input to the messages list
        messages.append({"role": "user", "content": f"{message}"})

        # if the user input is 'exit' or 'quit' break the loop
        if message.lower() in ["exit", "quit"]:
            break

        # get the completions/response from the model
        comp = client.chat.completions.create(
            model="LLaMA_CPP",
            messages=messages
        )
        
        # append the model response to the messages list for the next iteration
        messages.append({"role": f"{comp.choices[0].message.role}", "content": f"{comp.choices[0].message.content}"})

        # print the model response
        print(f"\nModel> {comp.choices[0].message.content}\n")