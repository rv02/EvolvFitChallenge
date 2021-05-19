package com.rv02.evolvFit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Parent_Child_Comment")
public class ParentChildComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "id")
    private Comment parentCommentID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child_comment_id", referencedColumnName = "id")
    private Comment childCommentID;

    public ParentChildComment(Comment parentCommentID, Comment childCommentID) {
        this.parentCommentID = parentCommentID;
        this.childCommentID = childCommentID;
    }

    public ParentChildComment() {
    }

    public Comment getParentCommentID() {
        return parentCommentID;
    }

    public void setParentCommentID(Comment parentCommentID) {
        this.parentCommentID = parentCommentID;
    }

    public Comment getChildCommentID() {
        return childCommentID;
    }

    public void setChildCommentID(Comment childCommentID) {
        this.childCommentID = childCommentID;
    }
}
