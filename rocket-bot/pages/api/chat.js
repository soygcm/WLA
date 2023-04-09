import { OpenAI } from "langchain";


export default async function (req, res) {

  // const stream = new ReadableStream({
    
  // })

  const model = new OpenAI({ 
    openAIApiKey: process.env.OPENAI_API_KEY, 
    temperature: 0.9,
    // streaming: true,
    // callbackManager:  CallbackManager.fromHandlers({
    //   async handleLLMNewToken(token) {
    //     console.log(token);
    //   },
    // }),
  });

  console.log("question", req.body.question)

  const result = await model.call(
    req.body.history + "\n" +
    req.body.question
  );

  // const response = await fetch(process.env.LCC_ENDPOINT_URL, {
  //   method: "POST",
  //   headers: {
  //     "Content-Type": "application/json",
  //     "X-Api-Key": process.env.LCC_TOKEN
  //   },
  //   body: JSON.stringify({
  //     question: req.body.question,
  //     history: req.body.history
  //   }),
  // });

    // const data = await response.json();
    console.log("result", result)

    res.status(200).json({ result: {success: result} })
}