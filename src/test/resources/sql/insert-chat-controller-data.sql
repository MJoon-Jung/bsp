INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (1, 'example', 'example@email.com', 'examplepassword', 'MEMBER', '2017-06-15 11:40:21', null);
INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (2, 'example2', 'example2@email.com', 'example2password', 'MEMBER', '2017-08-21 05:23:10', null);
INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (3, 'example3', 'exampl3e@email.com', 'example3password', 'MEMBER', '2017-07-15 11:40:21', null);
INSERT INTO `user` (`id`, `nickname`, `email`, `password`, `role`, `created_at`, `updated_at`)
VALUES (4, 'example4', 'example4@email.com', 'example4password', 'MEMBER', '2017-09-21 05:23:10', null);

INSERT INTO `post` (`id`, `content`, `reward`, `item_name`, `street`, `location`, `status`,
                    `trade_type`, `created_at`, `updated_at`, `user_id`, `finder_id`)
VALUES (1, 'This is content exmaple', 10000, 'airpods', '서울특별시 강남구 역삼동 123-456',
        Point(37.123456, 127.123456), 'PENDING', 'DIRECT', '2018-06-15 11:40:21', null, 1, null);

INSERT INTO `post` (`id`, `content`, `reward`, `item_name`, `street`, `location`, `status`,
                    `trade_type`, `created_at`, `updated_at`, `user_id`, `finder_id`)
VALUES (2, 'This is content exmaple2', 20000, 'headset', '서울특별시 광진구 자양동 234-567',
        Point(36.423142, 124.321312), 'PENDING', 'DIRECT', '2018-08-21 05:23:10', null, 2, null);

INSERT INTO `chat_room` (`id`, `post_id`, `created_at`, `updated_at`)
VALUES (1, 1, '2018-06-15 11:40:21', null);
INSERT INTO `chat_room` (`id`, `post_id`, `created_at`, `updated_at`)
VALUES (2, 2, '2018-06-15 11:41:22', null);
INSERT INTO `chat_room` (`id`, `post_id`, `created_at`, `updated_at`)
VALUES (3, 2, '2018-06-15 11:41:22', null);

INSERT INTO `chat_room_member` (`id`, `chat_room_id`, `user_id`, `created_at`, `updated_at`)
VALUES (1, 1, 1, '2018-06-15 11:40:21', '2018-06-15 11:40:21');

INSERT INTO `chat_room_member` (`id`, `chat_room_id`, `user_id`, `created_at`, `updated_at`)
VALUES (2, 1, 2, '2018-06-15 11:40:21', '2018-06-15 11:40:21');

INSERT INTO `chat_room_member` (`id`, `chat_room_id`, `user_id`, `created_at`, `updated_at`)
VALUES (3, 2, 2, '2018-06-15 11:40:21', '2018-06-15 11:40:21');

INSERT INTO `chat_room_member` (`id`, `chat_room_id`, `user_id`, `created_at`, `updated_at`,
                                `active`)
VALUES (4, 2, 1, '2018-06-15 11:40:21', '2018-06-15 11:40:21', false);
INSERT INTO `chat_room_member` (`id`, `chat_room_id`, `user_id`, `created_at`, `updated_at`)
VALUES (5, 3, 3, '2018-06-15 11:40:21', '2018-06-15 11:40:21');

INSERT INTO `chat_room_member` (`id`, `chat_room_id`, `user_id`, `created_at`, `updated_at`,
                                `active`)
VALUES (6, 3, 2, '2018-06-15 11:40:21', '2018-06-15 11:40:21', false);