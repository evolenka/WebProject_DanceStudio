INSERT INTO `type_of_membership` (
    `title`,
	`max_class_quantity`,
	`price`
) VALUES (
   	"GUEST",
	"1",
	"15"
	);
INSERT INTO `type_of_membership` (
    `title`,
	`max_class_quantity`,
	`price`
) VALUES (
   	"LIGHT",
	"4",
	"48"
	);
INSERT INTO `type_of_membership` (
    `title`,
	`max_class_quantity`,
	`price`
) VALUES (
   	"BASIC",
	"8",
	"80"
	);
INSERT INTO `type_of_membership` (
    `id`,
    `title`,
	`max_class_quantity`,
	`price`
) VALUES (
    "4",
   	"STRONG",
	"16",
	"150"
	);
INSERT INTO `type_of_membership` (
    `id`,
    `title`,
	`price`
) VALUES (
     "5",
   	"UNLIM",
	"190"
	);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`,
	`balance_quantity`	
) VALUES (
   	"6",
	"2",
	"2022-04-01",
	"2022-05-01",
	"4"
	);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`,
	`balance_quantity`
) VALUES (
    "6",
	"1",
	"2022-03-28",
	"2022-03-29",
	"1"	
	);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`,
	`balance_quantity`
) VALUES (
    "7",
	"1",
	"2022-04-02",
	"2022-04-03",
	"1"	
	);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`,
	`balance_quantity`
) VALUES (
    "7",
	"3",
	"2022-04-07",
	"2022-05-07",
	"8"	
	);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`
	) VALUES (
    "8",
	"5",
	"2022-04-10",
	"2022-05-10"
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`, 
	`balance_quantity`
	) VALUES (
    "9",
	"4",
	"2022-04-10",
	"2022-05-10",
	"16"
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`, 
	`balance_quantity`
	) VALUES (
    "6",
	"4",
	"2022-06-01",
	"2022-07-01",
	"16"
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`, 
	`balance_quantity`
	) VALUES (
    "7",
	"3",
	"2022-06-01",
	"2022-07-01",
	"8"
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`
	
	) VALUES (
    "8",
	"5",
	"2022-06-01",
	"2022-07-01"
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`, 
	`balance_quantity`
	) VALUES (
    "9",
	"2",
	"2022-06-01",
	"2022-07-01",
	"4"
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`, 
	`balance_quantity`
	) VALUES (
    "10",
	"4",
	"2022-06-01",
	"2022-07-01",
	"16"
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`
	) VALUES (
    "11",
	"5",
	"2022-06-01",
	"2022-07-01"
	
);
INSERT INTO `membership` (
    `client_id`,
	`type_of_membership_id`,
	`start_date`,
	`end_date`, 
	`balance_quantity`
	) VALUES (
    "6",
	"1",
	"2022-06-08",
	"2022-06-08",
	"1"
);