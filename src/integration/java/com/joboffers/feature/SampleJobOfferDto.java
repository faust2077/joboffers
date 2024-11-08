package com.joboffers.feature;

public interface SampleJobOfferDto {
    default String bodyWithZeroOffersJson() {
        return "[]";
    }

    default String bodyWithTwoOffersJson() {
        return """ 
        [
            {
                "title": "Junior Java Developer",
                "company": "BlueSoft Sp. z o.o.",
                "salary": "7 000 - 9 000 PLN",
                "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
            },
            {
                "title": "Java (CMS) Developer",
                "company": "Efigence SA",
                "salary": "16 000 - 18 000 PLN",
                "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
            }
        ]
        """.trim();
    }
    default String bodyWithFourOffersJson() {
        return """ 
        [
            {
                "title": "Junior Java Developer",
                "company": "BlueSoft Sp. z o.o.",
                "salary": "7 000 - 9 000 PLN",
                "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
            },
            {
                "title": "Java (CMS) Developer",
                "company": "Efigence SA",
                "salary": "16 000 - 18 000 PLN",
                "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
            },
            {
                "title": "Junior Java Developer",
                "company": "Sollers Consulting",
                "salary": "7 500 - 11 500 PLN",
                "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"
            },
            {
                "title": "Junior Full Stack Developer",
                "company": "Vertabelo S.A.",
                "salary": "7 000 - 9 000 PLN",
                "offerUrl": "https://nofluffjobs.com/pl/job/junior-full-stack-developer-vertabelo-remote-k7m9xpnm"
            }
        ]
    """.trim();
    }
}
