import ICommentResponse from "main/api/comments/dto/ICommentResponse";
import * as React from 'react';
import CommentRow from "./CommentRow";

interface IProps {
  comments: ICommentResponse[]
  refreshComments(): void
}

const CommentRows: React.FC<IProps> = ({ comments, refreshComments }) => {
  return <>{comments.map((comment, index) =>
    <CommentRow key={index} comment={comment} refreshComments={refreshComments} />)
  }</>
}


export default CommentRows;