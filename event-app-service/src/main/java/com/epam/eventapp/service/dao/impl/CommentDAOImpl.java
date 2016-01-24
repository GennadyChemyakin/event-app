package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

/**
 * CommentDAO implementation
 */
@Repository
public class CommentDAOImpl extends GenericDAO implements CommentDAO {

    private final static String GET_COMMENTS_LIST_OF_FIXED_SIZE_BY_EVENT_ID_BEFORE_TIME = "select comment_alias.*, rownum rnum " +
            "from (select c.id as c_id, c.message as c_message, c.comment_time as c_comment_time, c.event_id as c_event_id, " +
            "u.id as u_id, u.username as u_username, u.email as u_email, u.name as u_name, u.surname as u_surname, " +
            "u.country as u_country, u.city as u_city,u.bio as u_bio from commentary c join sec_user u on c.sec_user_id = u.id " +
            "where c.event_id=:eventId and c.comment_time < :commentTime ORDER BY c.COMMENT_TIME DESC) comment_alias " +
            "where rownum <= :amount";

    private final static String GET_COUNT_OF_REMAINING_COMMENTS = "select count(*) from commentary c where c.comment_time" +
            " < :commentTime and c.event_id=:eventId";

    @Override
    public Optional<List<Comment>> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, Timestamp commentTime, int amount) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("eventId", eventId);
        params.addValue("commentTime", commentTime);
        params.addValue("amount", amount);
        List<Comment> commentList = getNamedParameterJdbcTemplate().query(GET_COMMENTS_LIST_OF_FIXED_SIZE_BY_EVENT_ID_BEFORE_TIME,
                params,
                (resultSet, i) -> Comment.builder().
                        user(User.builder(resultSet.getString("u_username"), resultSet.getString("u_email")).
                                name(resultSet.getString("u_name")).
                                id(resultSet.getInt("u_id")).
                                surname(resultSet.getString("u_surname")).
                                country(resultSet.getString("u_country")).
                                city(resultSet.getString("u_city")).
                                bio(resultSet.getString("u_bio")).build()).
                        eventId(eventId).
                        message(resultSet.getString("c_message")).
                        timeStamp(resultSet.getTimestamp("c_comment_time").toLocalDateTime()).
                        id(resultSet.getInt("c_id")).
                        build());
        return commentList.size() > 0 ? Optional.of(commentList) : Optional.empty();

    }

    @Override
    public Integer countOfCommentsAddedBeforeDate(int eventId, Timestamp commentTime) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("eventId", eventId);
        params.addValue("commentTime", commentTime);
        return getNamedParameterJdbcTemplate().queryForObject(GET_COUNT_OF_REMAINING_COMMENTS, params, Integer.class);
    }
}
