import ICommentResponse from 'main/api/comments/dto/ICommentResponse';
import Maybe from 'main/ui/component/Maybe';
import CommentRows from 'main/ui/component/MentionTable/row/comment/CommentRows';
import { IRootState } from 'main/ui/modules';
import * as rowsModule from 'main/ui/modules/comments/rows';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';
import CommentAddFormContainer from './CommentAddFormContainer';

interface IProps {
  mentionId: number
  isAddingComment: boolean

  pending: boolean
  rejected: boolean
  dispatcher: typeof rowsModule

  finishAddingComment(): void
}

interface IState {
  comments: ICommentResponse[]
}

class CommentRowsContainer extends React.Component<IProps, IState> {
  public state = {
    comments: [] as ICommentResponse[]
  }

  public componentDidMount() {
    this.refreshComments()
  }

  public render() {
    const { comments } = this.state
    const { mentionId, isAddingComment, finishAddingComment } = this.props
    const { refreshComments } = this
    return <>
      <Maybe test={isAddingComment}>
        <CommentAddFormContainer
          mentionId={mentionId}
          finishAdding={finishAddingComment}
          refreshComments={refreshComments} />
      </Maybe>
      <CommentRows comments={comments} refreshComments={refreshComments} />
    </>
  }

  private setComments = (comments: ICommentResponse[]) => this.setState({ comments })
  private refreshComments = () => {
    const { getAndSetCommentList } = this.props.dispatcher
    const { mentionId } = this.props
    const { setComments } = this

    getAndSetCommentList(mentionId, setComments)
  }
}

const mapStateToProps = ({ comments }: IRootState) => ({
  pending: comments.rows.get("pending"),
  rejected: comments.rows.get("rejected")
})

const mapDispatchToProps = (dispatch: Dispatch<rowsModule.Action>) => ({
  dispatcher: bindActionCreators(rowsModule, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(CommentRowsContainer);