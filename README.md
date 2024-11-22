# Guess the star android app

## How to build

- Deploy API that returns celebrities as JSON array of objects with fields `image_url` and `name`. You can as well spin up deno server from this repo (`celebrity-api` folder)
- Create `.env` file in root directory with `API_URL` variable with URL to API. (note: you can't use localhost because android will be mad)
- Build project with gradle
