# AppStoreCrawler
App store crawler website running on top of Play Framework.
Both Google Play Store and Apple App Store are supported.

## Crawler
The crawler is a Java port of the project [GooglePlayAppsCrawler](https://github.com/MarcelloLins/GooglePlayAppsCrawler).

## Server
The server includes REST endpoint which directly returns a JSON object. 
Endpoints have the following structure:
* .../playstore/com.google.android.youtube 
* .../applestore/youtube%2Fid544007664
