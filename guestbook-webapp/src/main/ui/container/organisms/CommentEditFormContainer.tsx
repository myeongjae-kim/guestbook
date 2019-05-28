import ICommentPutRequest from 'main/api/comments/dto/ICommentPutRequest';
import ICommentResponse from 'main/api/comments/dto/ICommentResponse';
import CommentEditForm from 'main/ui/component/organisms/MentionTable/row/comment/CommentEditForm';
import { IRootState } from 'main/ui/modules';
import * as editFormModule from 'main/ui/modules/comments/edit-form';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';
import { TextAreaProps } from 'semantic-ui-react';

interface IProps {
  oldComment: ICommentResponse
  rejected: boolean
  dispatcher: typeof editFormModule

  finishEditing(): void
  refreshComments(): void
}

interface IState {
  commentPutRequest: ICommentPutRequest
}

class CommentEditFormContainer extends React.Component<IProps, IState> {
  public state = {
    commentPutRequest: {
      content: ""
    }
  }

  public componentDidMount() {
    this.setState({
      commentPutRequest: {
        ...this.props.oldComment
      }
    })
  }

  public render() {
    const { oldComment } = this.props;
    const { commentPutRequest } = this.state;
    const { putComment, changeContent } = this;
    return <CommentEditForm
      oldComment={oldComment}
      commentPutRequest={commentPutRequest}
      putComment={putComment}
      changeContent={changeContent} />
  }

  private putComment = () => {
    const { refreshComments } = this.props
    const { putComment } = this.props.dispatcher
    const { id } = this.props.oldComment
    const { commentPutRequest } = this.state

    putComment(id, commentPutRequest, refreshComments);
    this.props.finishEditing();
  }

  private changeContent = (_: React.FormEvent<HTMLTextAreaElement>, data: TextAreaProps) => {
    this.setState({
      commentPutRequest: {
        ...this.state.commentPutRequest,
        content: data.value as string,
      }
    })
  }
}

const mapStateToProps = ({ comments }: IRootState) => ({
  rejected: comments.editForm.get("rejected")
})

const mapDispatchToProps = (dispatch: Dispatch<editFormModule.Action>) => ({
  dispatcher: bindActionCreators(editFormModule, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(CommentEditFormContainer);