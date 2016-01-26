package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * CommentDAO implementation
 */
@Repository
public class CommentDAOImpl extends GenericDAO implements CommentDAO {

    private final static String GET_COMMENTS_LIST_OF_FIXED_SIZE_BY_EVENT_ID_BEFORE_TIME = "select comment_alias.*, rownum rnum " +
            "from (select c.id as c_id, c.message, c.comment_time, c.event_id, " +
            "u.id as u_id, u.username, u.email, u.name, u.surname, " +
            "u.country, u.city, u.bio from commentary c join sec_user u on c.sec_user_id = u.id " +
            "where c.event_id=:eventId and c.comment_time < :commentTime ORDER BY c.comment_time DESC) comment_alias " +
            "where rownum <= :amount";

    private final static String GET_COUNT_OF_REMAINING_COMMENTS = "select count(*) from commentary c where c.comment_time" +
            " < :commentTime and c.event_id=:eventId";

    @Override
    public List<Comment> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime commentTime, int amount) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("eventId", eventId);
        params.addValue("commentTime", Timestamp.valueOf(commentTime));
        params.addValue("amount", amount);
        List<Comment> commentList = getNamedParameterJdbcTemplate().query(GET_COMMENTS_LIST_OF_FIXED_SIZE_BY_EVENT_ID_BEFORE_TIME,
                params,
                (resultSet, i) -> Comment.builder().
                        user(User.builder(resultSet.getString("username"), resultSet.getString("email")).
                                name(resultSet.getString("name")).
                                id(resultSet.getInt("u_id")).
                                surname(resultSet.getString("surname")).
                                country(resultSet.getString("country")).
                                city(resultSet.getString("city")).
                                bio(resultSet.getString("bio")).build()).
                        eventId(eventId).
                        message(resultSet.getString("message")).
                        timeStamp(resultSet.getTimestamp("comment_time").toLocalDateTime()).
                        id(resultSet.getInt("c_id")).
                        build());
        return commentList;
    }

    @Override
    public int countOfCommentsAddedBeforeDate(int eventId, LocalDateTime commentTime) throws SQLException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("eventId", eventId);
        params.addValue("commentTime", Timestamp.valueOf(commentTime));
        Integer count =  getNamedParameterJdbcTemplate().queryForObject(GET_COUNT_OF_REMAINING_COMMENTS, params, Integer.class);
        if(count != null)
            return count.intValue();
        else
            throw new SQLException();
    }
}
