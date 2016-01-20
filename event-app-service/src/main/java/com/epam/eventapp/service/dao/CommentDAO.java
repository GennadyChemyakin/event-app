package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Created by gennady on 18.01.16.
 */
public interface CommentDAO {

    /**
     * method for getting list of fixed size of amount starting with offset of comments for event by event id
     * @param eventId event id
     * @param offset start position of commentary in list sorted by date
     * @param amount amount of comments to receive
     * @return Optional with list in case comments were found and otherwise Optional.empty()
     */
    Optional<List<Comment>> getCommentsListByEventId(int eventId, int offset, int amount);
}
