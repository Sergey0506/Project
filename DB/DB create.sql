-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Салаты'),(2,'Бургеры'),(3,'Закуски'),(4,'Десерты'),(5,'Гриль'),(6,'Гарниры'),(7,'Основные блюда из мяса');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `name` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(200) NOT NULL DEFAULT '',
  `price` int NOT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'avalable',
  PRIMARY KEY (`id`),
  KEY `category_idx` (`category_id`),
  CONSTRAINT `category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,1,'Цезарь с курицей и беконом','Салатный микс с обжаренным куриным филе и беконом в сочетании с помидорами черри, перепелиными яйцами и сыром Грана Падано под соусом Цезарь.',129,'avalable'),(2,1,'Салат Греческий','Свежие овощи (огурец, помидор, болгарский перец, красный лук) с сыром Фета и оливковым маслом.',98,'avalable'),(3,1,'Салат с морепродуктами','Тигровые креветки, мидии и кальмары обжаренные с апельсином в сочетании с миксом салата и оливковым маслом.',259,'avalable'),(4,3,'Сырные наггетс','Обжаренные ломтики сыра Моцарелла в панировке с соусом Тар-тар.',99,'avalable'),(5,3,'Брускетта с лососем и авокадо 3шт','Хрустящие тосты белого хлеба, с ломтиками слабосоленого лосося, ароматным маслом и слайсы авокадо.',104,'avalable'),(6,3,'Брускетта со свежими томатами и соусом Песто 3 шт','Хрустящие тосты из белого хлеба, со свежими помидорами и сыром Фета.',41,'avalable'),(7,5,'Овощи гриль','Кабачок, болгарский перец, баклажан, лук. шампиньоны.',89,'avalable'),(8,5,'Стейк Рибай без кости сух.ферм.','Отруб толстого края телятины (Рибай без кости) сухой ферментации в авторских специях.Вес указан при прожарке медиум.',239,'avalable'),(9,5,'Стейк Рибай на кости сухой ферм. 10д','Стейк Рибай сухой ферментации 10 д. Самый мясной стейк из премиальных стейков. Мясо для стейка Рибай на кости вырезается из подлопаточной части.Вес указан при прожарке медиум.',279,'avalable'),(10,5,'Куриные Крылья Барбекю','Куриные крылья в медовой глазури',148,'avalable'),(11,2,'Чиииз Бургер','Бифштекс с ферментированной говядины, сырный наггетс из сыра Моцарелла, салат Айсберг, красный лук, соус \"Цезарь\", булочка.',143,'avalable'),(12,2,'Чикен Бургер','Булочка, бифштекс из куриного филе с сыром Грана Падано, Голландский соус, луковый кранч, салат айсберг, маринованный огурец и сыр Мимолет.',123,'avalable'),(13,2,'Бургер \'Айова\'','Булочка, бифштекс с ферментированной говядины, соус BBQ, чипсы бекона, салат айсберг, кранч из лука, картофельный пай, слайсы сыра Моцарелла.',123,'avalable'),(14,2,'Бургер \'Мания\'','Пшеничная белая булочка с бифштексом из ферментированной говядины, соленым огурцом и свежим помидором в сочетании с соусом \"Пармезан\" и сырами Чеддер и Мимолет',189,'avalable'),(15,2,'Бургер \'Чиполи\' ','Булочка с бифштексом из ферментированной говядины, куриное яйцо, карамелизированный луком, с Голландским соусом',126,'avalable'),(16,6,'Картофель по-сицилийски','Обжаренная картошка в кожуре с вялеными томатами, чесноком и сыром Грана Падано.',52,'avalable'),(17,6,'Картофельное пюре с обжаренными лисичками','Картофельное пюре со сливками и сливочным маслом в сочетании с обжаренными лисичками и луком.',109,'avalable'),(18,4,'Красный Бархат','Торт Красный бархат - это сочетание сочных коржей с нежным сливочным крем.',84,'avalable'),(19,4,'Десерт Тирамису','Классический Итальянский десерт с сыром Маскарпоне, палочками Савоярди, кофе и сливочным ликером.',84,'avalable'),(20,4,'Панна котта с Дор-Блю','Сливки с сыром Дор-Блю и карамелизированною грушей.',84,'avalable'),(21,4,'Чизкейк Персиковый','Чизкейк с персиковым желе и карамельным топингом .',84,'avalable'),(22,7,'Телятина с картофельным пюре с васаби','Обжаренная телятина на гриле, с пюре из картофеля и васаби, под соусом Винегрет.',179,'avalable'),(23,7,'Сковородка «Поло»','Запеченное куриное филе с овощами и грибами под сливочным соусом со специями и сыром Моцарелла.',146,'avalable'),(24,7,'Сковородка «Витело»','Запеченная ферментированная телятина с картофелем, луком и грибами под сливочным соусом со специями и сыром Моцарелла.',146,'avalable'),(25,7,'Вырезка из свинины с ягодным соусом','Свиная вырезка обжаренная на гриле, подается соусом из ягод клубники и смородины.',169,'avalable'),(104,3,'Дуэт Тартаров','Филе лосося и тунца с каперсами, маринованные в соусе из Дижонской горчицы и Табаско',198,'avalable'),(105,3,'Тар-тар из телятины','Маринованная телятина с красным луком, каперсами и сыром Грана Падано',133,'avalable'),(106,3,'Тигровые креветки в чипсовой панировке','Тигровые креветки в панировке из чипсов и яйца в сочетании с соусом Пармезан',171,'avalable'),(107,3,'Угорь на сливочном авокадо','Слайсы копченого угря с авокадо, кремом сыром и салатом Айсберг',216,'avalable'),(108,3,'Фокачча с прованскими травами','Классическая итальянская пшеничная лепёшка с сыром Грана Падано',48,'avalable'),(109,5,'Сет BBQ','Куриные крылышки BBQ, печеные шампиньоны, сырные наггетс, обжаренное на гриле куриное филе с соусом Барбекю и соусом Валио',446,'avalable'),(110,5,'Ассорти морепродуктов','Тигровые креветки, мидии в ракушках и кальмары в сочетании с соусом Валио и миксом зелени',446,'avalable'),(111,5,'Гриль сет Колбас','Колбаска Мюнхенская, куриная колбаса, колбаса Тюренген с горчицей и соусом барбекю',469,'avalable'),(112,5,'Лента ребер с картофелем фри','Обжаренные ребрышки с картофелем фри и ягодным соусом и французской горчицей',454,'avalable'),(113,5,'Мясное ассорти на компанию','Ферментированная телятина, свиной ошеек, куриное филе, запеченный картофель, в сочетании с соусом Цезарь и Барбекюй',598,'avalable'),(114,5,'Колбаса Куриная','Курица и специи',134,'avalable'),(115,5,'Колбаса Мюнхенская','Свинина и специи',139,'avalable'),(116,5,'Колбаска Тюринген','Телятина и специи',136,'avalable'),(117,2,'Комбо-набор \'Чиккен-Бургер\'','Чиккен Бургер с двойной котлетой из куриного филе и картофелем фри',180,'avalable'),(118,2,'Комбо-набор \'Чииизбургер\'','Бургер \"Чииизбургер\" с двойным бифштексом из телятины и картофелем фри',197,'avalable'),(119,2,'Комбо-набор \'Бургер Айова\'','Бургер Айова,  Картофель фри, Пепси макс 0,5л',180,'avalable'),(120,2,'Комбо-набор \'Бургер Чиполли\'','Бургер \"Чиполли\" с двойным бифштексом из телятины и картофелем фри',180,'avalable'),(121,2,'Комбо-набор \'Бургер Мания\'','Бургер \"Мания\" с двойным бифштексом и картофелем фри',238,'avalable'),(122,4,'Торт Захер','Бисквитные коржи с шоколадом и абрикосовым конфитюром',84,'avalable'),(123,7,'Ножка утки Конфи','Томленая ножка утки с золотой корочкой в сочетании с соусом из красного вина и черной смородины.',234,'avalable'),(124,7,'Филе Дорадо на карпаччо из цуккини','Запеченное филе Дорадо из тапенада из маслин на слайсах цуккини аль денте',251,'avalable'),(126,7,'Запеченные свиные ребра с грушей и фисташкой','Свиные ребра запеченные с грушей под медово-горчичным соусом',166,'avalable'),(127,7,'Куриное филе в соусе Песто с белыми грибами','Замаринованное в соусе Песто и обжаренное куриное филе со сливочным соусом из белых грибов',156,'avalable');
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `sum` int NOT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'Новый',
  PRIMARY KEY (`id`),
  KEY `orders-users_idx` (`user_id`),
  CONSTRAINT `orders-users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_dishes`
--

DROP TABLE IF EXISTS `orders_dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_dishes` (
  `order_id` int NOT NULL,
  `dish_id` int NOT NULL,
  `count` int NOT NULL,
  PRIMARY KEY (`order_id`,`dish_id`),
  KEY `toMenu_idx` (`dish_id`),
  CONSTRAINT `toMenu` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `toOrder` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_dishes`
--

LOCK TABLES `orders_dishes` WRITE;
/*!40000 ALTER TABLE `orders_dishes` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders_dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(10) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'user',
  `locale` varchar(3) NOT NULL DEFAULT 'ua',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (42,'user','ee11cbb19052e40b07aac0ca060c23ee','user','ua'),(43,'manager','1d0258c2440a8d19e716292b231e3190','manager','ua');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-10 18:24:07
