const request = require('supertest');
const app = require('./app');

describe('POST /login', () => {
  it('responds with 200 status and a success message', async () => {
    const res = await request(app)
      .post('/login')
      .send({
        username: 'your_username',
        password: 'your_password'
      });
    expect(res.statusCode).toEqual(200);
    expect(res.body.message).toEqual('Login successful!');
  });

  it('responds with 401 status and an error message if login credentials are incorrect', async () => {
    const res = await request(app)
      .post('/login')
      .send({
        username: 'wrong_username',
        password: 'wrong_password'
      });
    expect(res.statusCode).toEqual(401);
    expect(res.body.message).toEqual('Invalid login credentials.');
  });
});
