const request = require('supertest');
const app = require('./app');

describe('GET /tweets', () => {
  it('responds with 200 status and an array of tweets', async () => {
    const res = await request(app)
      .get('/tweets');
    expect(res.statusCode).toEqual(200);
    expect(Array.isArray(res.body)).toBe(true);
  });
});
