import ICommentResponse from 'main/api/comments/dto/ICommentResponse';
import CommentRows from 'main/ui/component/organisms/MentionTable/row/comment/CommentRows';
import { IRootState } from 'main/ui/modules';
import * as rowsModule from 'main/ui/modules/comments/rows';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';

interface IProps {
  mentionId: number

  pending: boolean
  rejected: boolean
  dispatcher: typeof rowsModule
}

interface IState {
  comments: ICommentResponse[]
}

class CommentRowsContainer extends React.Component<IProps, IState> {
  public state = {
    comments: [] as ICommentResponse[]
  }

  public componentDidMount() {
    const { getAndSetCommentList } = this.props.dispatcher
    const { mentionId } = this.props
    const { setComments } = this

    getAndSetCommentList(mentionId, setComments)
  }

  public render() {
    const { comments } = this.state
    return <CommentRows comments={comments} />
  }

  private setComments = (comments: ICommentResponse[]) => this.setState({ comments })
}

const mapStateToProps = ({ comments }: IRootState) => ({
  pending: comments.rows.get("pending"),
  rejected: comments.rows.get("rejected")
})

const mapDispatchToProps = (dispatch: Dispatch<rowsModule.Action>) => ({
  dispatcher: bindActionCreators(rowsModule, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(CommentRowsContainer);