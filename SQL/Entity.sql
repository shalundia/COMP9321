drop table entity;
drop table relation;

create table entity(
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
	kind VARCHAR(20),
	e_class VARCHAR(20),
	title VARCHAR(200)
);
create table relation(
	subject INTEGER,
	predict INTEGER,
	object 	INTEGER
);

