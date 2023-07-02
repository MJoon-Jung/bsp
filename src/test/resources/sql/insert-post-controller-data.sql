INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (1, 'example', 'example@email.com', 'examplepassword', 'MEMBER', '2017-06-15 11:40:21', null);
INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (2, 'example2', 'example2@email.com', 'example2password', 'MEMBER', '2017-08-21 05:23:10', null);

INSERT INTO `post` (`id`, `title`, `content`, `reward`, `item_name`, `street`, `latitude`, `longitude`, `status`,
                    `trade_type`, `created_at`, `updated_at`, `user_id`, `finder_id`)
VALUES (1, 'This is title example', 'This is content exmaple', 10000, 'airpods', '서울특별시 강남구 역삼동 123-456', 37.123456,
        127.123456, 'PENDING', 'DIRECT', '2018-06-15 11:40:21', null, 1, null);

INSERT INTO `post` (`id`, `title`, `content`, `reward`, `item_name`, `street`, `latitude`, `longitude`, `status`,
                    `trade_type`, `created_at`, `updated_at`, `user_id`, `finder_id`)
VALUES (2, 'This is title example2', 'This is content exmaple2', 20000, 'headset', '서울특별시 광진구 자양동 234-567', 36.423142,
        124.321312, 'PENDING', 'DIRECT', '2018-08-21 05:23:10', null, 2, null);

INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `original_file_name`, `url`, `created_at`, `updated_at`)
VALUES (1, null, '123456_a.PNG', 'a.PNG', 'http://localhost:8080/upload/image/123456_a.PNG',
        '2023-06-29 10:50:20.120087', '2023-06-29 10:50:20.120087');
INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `original_file_name`, `url`, `created_at`, `updated_at`)
VALUES (2, null, '234567_b.PNG', 'b.PNG', 'http://localhost:8080/upload/image/234567_b.PNG',
        '2023-06-29 10:50:20.132023', '2023-06-29 10:50:20.132023');
INSERT INTO `image_post` (`id`, `post_id`, `file_name`, `original_file_name`, `url`, `created_at`, `updated_at`)
VALUES (3, null, '345678_c.PNG', 'c.PNG', 'http://localhost:8080/upload/image/345678_c.PNG',
        '2023-06-29 10:50:20.135397', '2023-06-29 10:50:20.135397');