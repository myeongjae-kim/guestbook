import ICommentResponse from "main/api/comments/dto/ICommentResponse";
import * as React from 'react';
import CommentRow from "./CommentRow";

interface IProps {
  comments: ICommentResponse[]
}

const CommentRows: React.FC<IProps> = ({ comments }) => {
  return <>{comments.map((comment, index) =>
    <CommentRow comment={comment} key={index} />)
  }</>
}


export default CommentRows;