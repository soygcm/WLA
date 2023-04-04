import { OpenAI } from "langchain";

const model = new OpenAI({ openAIApiKey: process.env.OPENAI_API_KEY, temperature: 0.9 });

export default async function (req, res) {


  console.log("question", req.body.question)

  const result = await model.call(
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