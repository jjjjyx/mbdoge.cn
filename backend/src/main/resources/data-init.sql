

insert into `mbdoge_user` (`id`, `created_at`, `display_name`, `next_expire_time`, `nickname`, `password`, `updated_at`, `user_status`, `username`, `ver`) VALUES ('1','2020-04-07 20:47:17', 'jyx', '2028-12-01 20:47:27', 'jyx', '59ca20b549032a6e85bb39315c30762c3393a428d8d1a317b3fa1667b9b3f8de4798e92d67db7065', '2020-04-07 20:47:52', '0', 'jjjjyx', '0');

insert into `mbdoge_config`(`name`,`text`,`value`) values ('setting.user.default.expire', '用户默认过期时间', '2592000000');

insert into `mbdoge_role`(`id`,`level`,`name`,`remark`,`created_at`) values (1, 100, 'admin','系统管理员', NULL);
insert into `mbdoge_role`(`id`,`level`,`name`,`remark`,`created_at`) values (2, 4, 'user','用户', NULL);
insert into `mbdoge_role`(`id`,`level`,`name`,`remark`,`created_at`) values (3, 0, 'guest','游客', NULL);

insert into `mbdoge_user_roles`(`userdo_id`,`roles_id`) values (1, 1);
insert into `mbdoge_user_roles`(`userdo_id`,`roles_id`) values (1, 2);