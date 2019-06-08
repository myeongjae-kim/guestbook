import DeleteButton from 'main/ui/component/DeleteButton';
import * as deleteButtonModule from 'main/ui/modules/comments/delete-button';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';

interface IProps {
  id: string
  dispatcher: typeof deleteButtonModule

  refreshComments(): void
}

const CommentDeleteButtonContainer: React.FC<IProps> = ({ id, dispatcher, refreshComments }) => {
  const deleteComment = () => {
    if (window.confirm('글을 지우시겠습니까?')) {
      dispatcher.deleteComment(id, refreshComments);
    }
    return;
  }
  return <DeleteButton del={deleteComment} />;
}

const mapDispatchToProps = (dispatch: Dispatch<deleteButtonModule.Action>) => ({
  dispatcher: bindActionCreators(deleteButtonModule, dispatch)
})

export default connect(null, mapDispatchToProps)(CommentDeleteButtonContainer);