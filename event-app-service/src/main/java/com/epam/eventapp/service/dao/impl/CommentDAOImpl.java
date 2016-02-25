package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.CommentaryNotAddedException;
import com.epam.eventapp.service.exceptions.ObjectNotDeletedException;
import com.epam.eventapp.service.model.QueryMode;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

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
            "where c.event_id=:eventId and c.comment_time < :before ORDER BY c.comment_time DESC) comment_alias " +
            "where rownum <= :amount";

    private final static String GET_COUNT_OF_COMMENTS = "select count(*) from commentary";

    private final static String ADDED_BEFORE_DATE = " where comment_time < :commentTime and event_id=:eventId";

    private final static String ADDED_AFTER_DATE = " where comment_time > :commentTime and event_id=:eventId";

    private final static String ADD_COMMENTARY = "insert into COMMENTARY (id, event_id, sec_user_id, comment_time, message)" +
            " VALUES(commentary_id_seq.nextval, :eventId, :userId, :commentTime, :message)";

    private final String GET_COMMENTS_LIST_BY_EVENT_ID_AFTER_DATE = "select c.id as c_id, c.message, c.comment_time, c.event_id, " +
            "u.id as u_id, u.username, u.email, u.name, u.surname, " +
            "u.country, u.city, u.bio from commentary c join sec_user u on c.sec_user_id = u.id " +
            "where c.event_id=:eventId and c.comment_time > :after ORDER BY c.comment_time DESC";

    private final static String DELETE_COMMENTARY_BY_ID = "delete from commentary where id=:id";

    @Override
    public List<Comment> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime before, int amount) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("eventId", eventId);
        params.addValue("before", Timestamp.valueOf(before));
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
                        commentTime(resultSet.getTimestamp("comment_time").toLocalDateTime()).
                        id(resultSet.getInt("c_id")).
                        build());
        return commentList;
    }

    @Override
    public int countCommentsAddedBeforeOrAfterDate(int eventId, LocalDateTime commentTime, QueryMode queryMode) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query;
        if (queryMode.equals(QueryMode.BEFORE)) {
            query = GET_COUNT_OF_COMMENTS + ADDED_BEFORE_DATE;
        } else if (queryMode.equals(QueryMode.AFTER)) {
            query = GET_COUNT_OF_COMMENTS + ADDED_AFTER_DATE;
        } else {
            throw new IllegalArgumentException("Unsupported query mode " + queryMode);
        }
        params.addValue("eventId", eventId);
        params.addValue("commentTime", Timestamp.valueOf(commentTime));
        return getNamedParameterJdbcTemplate().queryForObject(query, params, Integer.class);
    }

    @Override
    public void addComment(Comment comment) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("eventId", comment.getEventId()).addValue("userId", comment.getUser().getId()).
                addValue("commentTime", Timestamp.valueOf(comment.getCommentTime())).addValue("message", comment.getMessage());
        try {
            getNamedParameterJdbcTemplate().update(ADD_COMMENTARY, params);
        } catch (DataAccessException e) {
            throw new CommentaryNotAddedException(e.getMessage());
        }

    }

    @Override
    public List<Comment> getListOfNewComments(int eventId, LocalDateTime after) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("eventId", eventId);
        params.addValue("after", Timestamp.valueOf(after));
        List<Comment> commentList = getNamedParameterJdbcTemplate().query(GET_COMMENTS_LIST_BY_EVENT_ID_AFTER_DATE,
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
                        commentTime(resultSet.getTimestamp("comment_time").toLocalDateTime()).
                        id(resultSet.getInt("c_id")).
                        build());
        return commentList;
    }

    @Override
    public void deleteCommentById(int id) {
        int rows = getNamedParameterJdbcTemplate().update(DELETE_COMMENTARY_BY_ID, Collections.singletonMap("id", id));
        if (rows == 0)
            throw new ObjectNotDeletedException("commentary not deleted by id = " + id);
    }
}
