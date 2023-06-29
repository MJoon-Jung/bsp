INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (1, 'example', 'example@email.com', 'examplepassword', 'MEMBER', '2017-06-15 11:40:21', null);
INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (2, 'example2', 'example2@email.com', 'example2password', 'MEMBER', '2017-08-21 05:23:10', null);

INSERT INTO `post` (`id`, `title`, `content`, `reward`, `item_name`, `street`, `latitude`, `longitude`, `status`, `trade_type`, `created_at`, `updated_at`, `user_id`, `finder_id`)
VALUES (1, 'This is title example', 'This is content exmaple', 10000, 'airpods', '서울특별시 강남구 역삼동 123-456', 37.123456, 127.123456, 'PENDING', 'DIRECT', '2018-06-15 11:40:21', null, 1, null);

INSERT INTO `post` (`id`, `title`, `content`, `reward`, `item_name`, `street`, `latitude`, `longitude`, `status`, `trade_type`, `created_at`, `updated_at`, `user_id`, `finder_id`)
VALUES (2, 'This is title example2', 'This is content exmaple2', 20000, 'headset', '서울특별시 광진구 자양동 234-567', 36.423142, 124.321312, 'PENDING', 'DIRECT', '2018-08-21 05:23:10', null, 2, null);

INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `url`, `file_type`, `file_size`, `created_at`, `updated_at`)
VALUES (1, null, 'a.PNG', 'http://localhost:8080/upload/image/20230629105020117715a.PNG', 'PNG', 2830,
        '2023-06-29 10:50:20.120087', '2023-06-29 10:50:20.120087');
INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `url`, `file_type`, `file_size`, `created_at`, `updated_at`)
VALUES (2, null, 'b.PNG', 'http://localhost:8080/upload/image/20230629105020131563b.PNG', 'PNG', 13616,
        '2023-06-29 10:50:20.132023', '2023-06-29 10:50:20.132023');
INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `url`, `file_type`, `file_size`, `created_at`, `updated_at`)
VALUES (3, null, 'c.PNG', 'http://localhost:8080/upload/image/20230629105020134958c.PNG', 'PNG', 11876,
        '2023-06-29 10:50:20.135397', '2023-06-29 10:50:20.135397');
INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `url`, `file_type`, `file_size`, `created_at`, `updated_at`)
VALUES (4, null, 'a.PNG', 'http://localhost:8080/upload/image/20230629105021586896a.PNG', 'PNG', 2830,
        '2023-06-29 10:50:21.587548', '2023-06-29 10:50:21.587548');
INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `url`, `file_type`, `file_size`, `created_at`, `updated_at`)
VALUES (5, null, 'b.PNG', 'http://localhost:8080/upload/image/20230629105021589984b.PNG', 'PNG', 13616,
        '2023-06-29 10:50:21.590319', '2023-06-29 10:50:21.590319');
INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `url`, `file_type`, `file_size`, `created_at`, `updated_at`)
VALUES (6, null, 'c.PNG', 'http://localhost:8080/upload/image/20230629105021592880c.PNG', 'PNG', 11876,
        '2023-06-29 10:50:21.593593', '2023-06-29 10:50:21.593593');

