package com.pi.problem.interfaces;

public interface IReact {
    boolean verifyUserReaction(long id_user, int id_comment);

    String setReaction(int id_comment,long id_user);
}
