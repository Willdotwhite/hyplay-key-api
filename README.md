# HYPLAY Key API

## What on earth is this?

If you're using the HYPLAY storage API (and/or any other bits that require the `x-app-authorization` header) in your HYPLAY jam entry,
you've got two options:
* Embed your app secret in your game
* Host an API to manage your secret somewhere else

Building a whole API isn't a small task. This API is aiming to fill that gap.

## How does it work?

There's only one piece of code in this API. If you hit this API with _any_ path, query parameters, headers etc., this
API passes the request onto `https://api.hyplay.com...` with the `x-app-authorization` header added to the request.

So if you send an API request from your game to `https://{this-api-domain.com}/v1/apps/{app-id}/states`, the API
creates a new request to `https://api.hyplay.com/v1/apps/{app-id}/states` with your app secret attached.

## How do I run it?

* Host this image (@dotwo can provide advice)
  * I recommend digitalocean.com, who have $200 of credits to use within 60 days at this link: https://try.digitalocean.com/freetrialoffer/
  * Use the App Platform (https://www.digitalocean.com/products/app-platform) to host this API, which requires very little setup
* When setting up your app, add this environment variable:
  * Key: `HYPLAY_API_KEY`
  * Value: `app_sk...` (or `test_app_sk...` for your app's test secret)
* Whenever you want to call HYPLAY's API, replace `https://api.hyplay.com` with the URL your app is hosted on