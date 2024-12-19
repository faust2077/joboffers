db = db.getSiblingDB("joboffers-web");
db.createUser(
    {
        user: "admin",
        pwd: "0",
        roles: [
            {
                role: "readWrite",
                db: "joboffers-web"
            }
        ]
    }
)