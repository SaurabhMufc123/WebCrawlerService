# WebCrawlerServices

Java based web service to crawl websites and search for the text entered.

## Getting Started

- Run the spring boot application
- Once running, access the application at http://localhost:8081/text. It takes two input:
    - List of URLs to crawl
    - Keyword to be searched

Sample Request:
```
{
    "urls": [
        "https://en.wikipedia.org/wiki/Main_Page"
    ],
    "keyword": "Wikipedia"
}
```

## Design Considrations
- Used Jsoup library to implement the webcrawler.s
- Global config for maximum urls to crawl per job and maximum depth while crawling.

## Future Roadmap
- Async crawling