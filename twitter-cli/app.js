const express = require('express');
const app = express();

app.use(express.json());

app.post('/login', (req, res) => {
  const { username, password } = req.body;
  if (username === 'your_username' && password === 'your_password') {
    res.status(200).json({ message: 'Login successful!' });
  } else {
    res.status(401).json({ message: 'Invalid login credentials.' });
  }
});

app.get('/tweets', (req, res) => {
  // logic to retrieve last tweets goes here
  res.status(200).json([]);
});

module.exports = app;
