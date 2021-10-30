create table menu_categories
(
    id       int auto_increment,
    category varchar(20) null,
    constraint menu_categories_id_uindex
        unique (id)
);

alter table menu_categories
    add primary key (id);

create table menu_items
(
    id          int auto_increment,
    name        varchar(40)   null,
    description mediumtext    null,
    price       decimal(7, 2) null,
    status      tinyint(1)    null,
    image       varchar(40)   null,
    category_id int           null,
    constraint menu_items_id_uindex
        unique (id),
    constraint menu_items_menu_categories_id_fk
        foreign key (category_id) references menu_categories (id)
);

alter table menu_items
    add primary key (id);

create table order_menu_item_mapping
(
    id           int auto_increment,
    menu_item_id int null,
    order_id     int null,
    constraint menu_item_order_mapping_id_uindex
        unique (id)
);

create index menu_item_order_mapping_menu_items_id_fk
    on order_menu_item_mapping (menu_item_id);

create index menu_item_order_mapping_orders_id_fk
    on order_menu_item_mapping (order_id);

alter table order_menu_item_mapping
    add primary key (id);

create table order_status
(
    id     int auto_increment,
    status varchar(15) null,
    constraint order_status_id_uindex
        unique (id)
);

alter table order_status
    add primary key (id);

create table user_role
(
    id   int auto_increment,
    role varchar(15) null,
    constraint client_role_id_uindex
        unique (id)
);

alter table user_role
    add primary key (id);

create table user_status
(
    id     int auto_increment,
    status varchar(15) null,
    constraint client_status_id_uindex
        unique (id)
);

alter table user_status
    add primary key (id);

create table users
(
    id            int auto_increment,
    name          varchar(20)   null,
    surname       varchar(20)   null,
    email         varchar(30)   null,
    password      varchar(70)   null,
    role_id       int           null,
    status_id     int           null,
    balance       decimal(7, 2) null,
    profile_image varchar(60)   null,
    constraint users_id_uindex
        unique (id),
    constraint users_user_role_id_fk
        foreign key (role_id) references user_role (id),
    constraint users_user_status_id_fk
        foreign key (status_id) references user_status (id)
);

alter table users
    add primary key (id);

create table orders
(
    id           int auto_increment,
    user_id      int           null,
    pick_up_time timestamp     null,
    total_price  decimal(7, 2) null,
    status_id    int           null,
    date         timestamp     null,
    constraint orders_id_uindex
        unique (id),
    constraint orders_order_status_id_fk
        foreign key (status_id) references order_status (id),
    constraint orders_users_id_fk
        foreign key (user_id) references users (id)
);

alter table orders
    add primary key (id);

create table reviews
(
    id       int auto_increment,
    order_id int        null,
    comment  mediumtext null,
    date     datetime   null,
    rating   tinyint    null,
    constraint reviews_id_uindex
        unique (id),
    constraint reviews_orders_id_fk
        foreign key (order_id) references orders (id)
);

alter table reviews
    add primary key (id);


