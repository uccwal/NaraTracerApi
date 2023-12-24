docker pull mongo

docker run -d --name mongodb -v C:/mongo/data:/data/db -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root -p 27017:27017 mongo

mongosh -u root -p root
use admin;
db.createUser(
  {
    user: "mongo",
    pwd:  "mongo",
    roles: [
    	{ "role" : "root", "db" : "admin" },
	]
  }
)
use mydb;
db.createCollection("TB_NOTICE")


db.TB_NOTICE.find({
    "category":"용역",
    "bidStart": {
        $gte: "2023-12-11",
        $lte: "2023-12-25"
    },
    $or: [
        { "bidder": /소방서|한국출판문화산업진흥원|한국환경산업기술원|정보통신산업진흥원|인사혁신처|소상공인시장진흥공단|공정거래위원회|한국공정거래조정원|한국여성인권진흥원|한국국토정보공사|인천공항공사|산림청|산림교육원|문화재청|문화체육관광부|SBA|서울산업진흥원|축산물품질평가원|질병관리본부|기초과학연구원|연구개발특구진흥재단|서울신용보증재단|방송통신위원회|한국관광공사|법무부|여성가족부|금융위원회|외교부|환경산업기술원|국회사무처|대검찰청|국방정보본부|태권도진흥재단|보건복지부|인천국제공항공사|국방|국방부|국방부(운영지원과)/,
            "titleLinkText": /소방서|디지털|시스템|유지보수|유지관리|고도화|플랫폼|산림청|솔루션|챗봇/}
    ]

}).pretty();


db.TB_NOTICE.find({
    "category": "용역",
    "bidStart": {
        "$gte": "2023-12-11",
        "$lte": "2023-12-25"
    },
    "$or": [
        {
            "bidder": {
                "$regex": "소방서|한국출판문화산업진흥원|한국환경산업기술원|정보통신산업진흥원|인사혁신처|소상공인시장진흥공단|공정거래위원회|한국공정거래조정원|한국여성인권진흥원|한국국토정보공사|인천공항공사|산림청|산림교육원|문화재청|문화체육관광부|SBA|서울산업진흥원|축산물품질평가원|질병관리본부|기초과학연구원|연구개발특구진흥재단|서울신용보증재단|방송통신위원회|한국관광공사|법무부|여성가족부|금융위원회|외교부|환경산업기술원|국회사무처|대검찰청|국방정보본부|태권도진흥재단|보건복지부|인천국제공항공사|국방|국방부|국방부(운영지원과)",
                "$options": "i"
            }
        },
        {
            "titleLinkText": {
                "$regex": "소방서|디지털|시스템|유지보수|유지관리|고도화|플랫폼|산림청|솔루션|챗봇",
                "$options": "i"
            }
        }
    ]
}).pretty();