create table post(
	uuid varchar(36) primary key,
	title text not null,
	content text,
	publishing_date date
)engine=InnoDB;

create table comment(
	uuid varchar(36) primary key,
	post_uuid varchar(36) references post(uuid),
	author text not null,
	content text not null,
	approved boolean not null default false,
	submission_date date
)engine=InnoDB;

create table post_category(
	post_uuid varchar(36) references post(uuid),
	category text
)engine=InnoDB;
