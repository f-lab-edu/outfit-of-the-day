DROP TABLE if EXISTS follow;
DROP TABLE if EXISTS post_like;
DROP TABLE if EXISTS comment_like;
DROP TABLE if EXISTS comment;
DROP TABLE if EXISTS post;
DROP TABLE if EXISTS history;
DROP TABLE if EXISTS member;

CREATE TABLE `member`
(
    `member_id`         bigint PRIMARY KEY AUTO_INCREMENT,
    `login_id`          varchar(64) UNIQUE NOT NULL,
    `password`          varchar(64)        NOT NULL,
    `nickname`          varchar(64) UNIQUE NOT NULL,
    `profile_message`   varchar(255),
    `profile_image_url` varchar(255),
    `created_at`        timestamp          NOT NULL,
    `updated_at`        timestamp          NOT NULL,
    `created_by`        varchar(64)        NOT NULL,
    `updated_by`        varchar(64)        NOT NULL,
    `is_deleted`        boolean            NOT NULL DEFAULT false
);

CREATE TABLE `comment`
(
    `comment_id`        bigint PRIMARY KEY,
    `post_id`           bigint      NOT NULL,
    `parent_comment_id` bigint      NOT NULL COMMENT '첫 번째 댓글인 경우 자기 자신, 대댓글인 경우 comment_id',
    `writer_comment_id` bigint      NOT NULL,
    `content`           text        NOT NULL,
    `depth`             int         NOT NULL,
    `created_at`        timestamp   NOT NULL,
    `updated_at`        timestamp   NOT NULL,
    `created_by`        varchar(64) NOT NULL,
    `updated_by`        varchar(64) NOT NULL,
    `is_deleted`        boolean     NOT NULL DEFAULT false
);

CREATE TABLE `post`
(
    `post_id`           bigint PRIMARY KEY,
    `writer_member_id`  bigint       NOT NULL,
    `title`             varchar(255) NOT NULL,
    `short_description` varchar(255) NOT NULL,
    `content`           text         NOT NULL,
    `post_status`       varchar(32)  NOT NULL,
    `created_at`        timestamp    NOT NULL,
    `updated_at`        timestamp    NOT NULL,
    `created_by`        varchar(64)  NOT NULL,
    `updated_by`        varchar(64)  NOT NULL,
    `is_deleted`        boolean      NOT NULL DEFAULT false
);


CREATE TABLE `post_like`
(
    `post_like_id` bigint PRIMARY KEY,
    `member_id`    bigint      NOT NULL,
    `post_id`      bigint      NOT NULL,
    `created_at`   timestamp   NOT NULL,
    `updated_at`   timestamp   NOT NULL,
    `created_by`   varchar(64) NOT NULL,
    `updated_by`   varchar(64) NOT NULL,
    `is_deleted`   boolean     NOT NULL DEFAULT false
);

CREATE TABLE `comment_like`
(
    `comment_like_id` bigint PRIMARY KEY,
    `member_id`       bigint      NOT NULL,
    `comment_id`      bigint      NOT NULL,
    `created_at`      timestamp   NOT NULL,
    `updated_at`      timestamp   NOT NULL,
    `created_by`      varchar(64) NOT NULL,
    `updated_by`      varchar(64) NOT NULL,
    `is_deleted`      boolean     NOT NULL DEFAULT false
);

CREATE TABLE `follow`
(
    `follow_id`      bigint PRIMARY KEY,
    `from_member_id` bigint      NOT NULL,
    `to_member_id`   bigint      NOT NULL,
    `created_at`     timestamp   NOT NULL,
    `updated_at`     timestamp   NOT NULL,
    `created_by`     varchar(64) NOT NULL,
    `updated_by`     varchar(64) NOT NULL,
    `is_deleted`     boolean     NOT NULL DEFAULT false
);

CREATE TABLE `history`
(
    `history_id`     bigint PRIMARY KEY,
    `table_name`     varchar(32) NOT NULL,
    `operation_type` varchar(32) NOT NULL,
    `snapshot`       json        NOT NULL,
    `created_at`     timestamp   NOT NULL,
    `created_by`     varchar(64) NOT NULL
);

ALTER TABLE `comment`
    ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`);

ALTER TABLE `post`
    ADD FOREIGN KEY (`writer_member_id`) REFERENCES `member` (`member_id`);

ALTER TABLE `comment`
    ADD FOREIGN KEY (`writer_comment_id`) REFERENCES `member` (`member_id`);

ALTER TABLE `post_like`
    ADD FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`);

ALTER TABLE `post_like`
    ADD FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`);

ALTER TABLE `comment_like`
    ADD FOREIGN KEY (`comment_id`) REFERENCES `comment` (`comment_id`);

ALTER TABLE `comment_like`
    ADD FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`);

ALTER TABLE `follow`
    ADD FOREIGN KEY (`from_member_id`) REFERENCES `member` (`member_id`);

ALTER TABLE `follow`
    ADD FOREIGN KEY (`to_member_id`) REFERENCES `member` (`member_id`);
