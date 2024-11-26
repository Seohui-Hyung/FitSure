-- 테이블 초기화
DROP TABLE IF EXISTS `admin`, `coupon`, `goal`, `goal_result`, `insurance_plans`, `notice`, `payments`, `subscribe`, `user`;

-- admin 테이블 생성
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(100) NOT NULL,
  `admin_password` varchar(255) NOT NULL,
  `manager` varchar(45) NOT NULL DEFAULT '관리자',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- coupon 테이블 생성
CREATE TABLE `coupon` (
  `coupon_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `coupon_date` date NOT NULL,
  `used` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`coupon_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- goal 테이블 생성
CREATE TABLE `goal` (
  `goal_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `day_goal` int DEFAULT '0',
  `week_goal` int DEFAULT '0',
  `start_day` datetime DEFAULT CURRENT_TIMESTAMP,
  `expire_day` datetime DEFAULT NULL,
  `exchange` tinyint(1) DEFAULT '0',
  `cancel` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`goal_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `goal_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- goal_result 테이블 생성
CREATE TABLE `goal_result` (
  `result_id` int NOT NULL AUTO_INCREMENT,
  `goal_id` int DEFAULT NULL,
  `walking` int DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `achieve` tinyint(1) DEFAULT NULL,
  `achieve_date` date DEFAULT NULL,
  PRIMARY KEY (`result_id`),
  KEY `goal_id` (`goal_id`),
  CONSTRAINT `goal_result_ibfk_1` FOREIGN KEY (`goal_id`) REFERENCES `goal` (`goal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- insurance_plans 테이블 생성
CREATE TABLE `insurance_plans` (
  `insurance_id` int NOT NULL AUTO_INCREMENT,
  `insurance_type` int NOT NULL,
  `insurance_name` varchar(45) NOT NULL,
  `admin_id` int DEFAULT '1',
  `premium` int NOT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `validity` int DEFAULT '1',
  PRIMARY KEY (`insurance_id`),
  KEY `insurance_plans_ibfk_1` (`admin_id`),
  CONSTRAINT `insurance_plans_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- notice 테이블 생성
CREATE TABLE `notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT,
  `admin_id` int NOT NULL,
  `title` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `reg_date` date NOT NULL,
  `view_cnt` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`notice_id`),
  KEY `notice_ibfk_1` (`admin_id`),
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- payments 테이블 생성
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `insurance_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_date` date NOT NULL,
  `refund` tinyint(1) NOT NULL,
  `payment_state` int DEFAULT '0',
  PRIMARY KEY (`payment_id`),
  KEY `user_id` (`user_id`),
  KEY `insurance_id` (`insurance_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`insurance_id`) REFERENCES `insurance_plans` (`insurance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- subscribe 테이블 생성
CREATE TABLE `subscribe` (
  `subscribe_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `insurance_id` int NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `discount_rate` decimal(3,2) DEFAULT NULL,
  `discount_validate` date NOT NULL,
  PRIMARY KEY (`subscribe_id`),
  KEY `user_id` (`user_id`),
  KEY `insurance_id` (`insurance_id`),
  CONSTRAINT `subscribe_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `subscribe_ibfk_2` FOREIGN KEY (`insurance_id`) REFERENCES `insurance_plans` (`insurance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- user 테이블 생성
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_loginid` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `gender` enum('M','F') DEFAULT NULL,
  `available` int DEFAULT '1',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
