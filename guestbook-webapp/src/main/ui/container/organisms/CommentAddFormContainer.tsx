import ICommentPostRequest from 'main/api/comments/dto/ICommentPostRequest';
import CommentAddForm from 'main/ui/component/organisms/MentionTable/row/comment/CommentAddForm';
import { IRootState } from 'main/ui/modules';
import * as addFormModule from 'main/ui/modules/comments/add-form';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';
import { TextAreaProps } from 'semantic-ui-react';

interface IProps {
  mentionId: number
  rejected: boolean
  dispatcher: typeof addFormModule

  finishAdding(): void
  refreshComments(): void
}

interface IState {
  commentPostRequest: ICommentPostRequest
}

class CommentAddFormContainer extends React.Component<IProps, IState> {
  public state = {
    commentPostRequest: {
      mentionId: this.props.mentionId,
      name: "",
      content: ""
    }
  }

  public render() {
    const { commentPostRequest } = this.state;
    const { postComment, changeName, changeContent } = this;
    return <CommentAddForm
      commentPostRequest={commentPostRequest}
      postComment={postComment}
      changeName={changeName}
      changeContent={changeContent} />
  }

  private postComment = () => {
    const { finishAddingAndRefreshComments } = this
    const { postComment } = this.props.dispatcher
    const { commentPostRequest } = this.state

    postComment(commentPostRequest, finishAddingAndRefreshComments);
  }

  private changeContent = (_: React.FormEvent<HTMLTextAreaElement>, data: TextAreaProps) => {
    this.setState({
      commentPostRequest: {
        ...this.state.commentPostRequest,
        content: data.value as string,
      }
    })
  }

  private changeName = (e: React.ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      commentPostRequest: {
        ...this.state.commentPostRequest,
        name: e.target.value,
      }
    })
  }

  private finishAddingAndRefreshComments = () => {
    const { refreshComments, finishAdding } = this.props
    finishAdding();
    refreshComments();
  }
}

const mapStateToProps = ({ comments }: IRootState) => ({
  rejected: comments.addForm.get("rejected")
})

const mapDispatchToProps = (dispatch: Dispatch<addFormModule.Action>) => ({
  dispatcher: bindActionCreators(addFormModule, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(CommentAddFormContainer);