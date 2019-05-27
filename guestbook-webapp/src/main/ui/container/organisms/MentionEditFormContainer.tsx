import IMentionRequest from 'main/api/mentions/dto/IMentionRequest';
import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import MentionEditForm from 'main/ui/component/organisms/MentionTable/row/mention/MentionEditForm';
import { IRootState } from 'main/ui/modules';
import * as editFormModule from 'main/ui/modules/mentions/edit-form';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';
import { TextAreaProps } from 'semantic-ui-react';

interface IProps {
  oldMention: IMentionResponse
  rejected: boolean
  dispatcher: typeof editFormModule

  finishEditing(): void
}

interface IState {
  mentionRequest: IMentionRequest
}

class MentionEditFormContainer extends React.Component<IProps, IState> {
  public state = {
    mentionRequest: {
      name: "",
      content: ""
    }
  }

  public componentDidMount() {
    this.setState({
      mentionRequest: {
        ...this.props.oldMention
      }
    })
  }

  public render() {
    const { oldMention } = this.props;
    const { mentionRequest } = this.state;
    const { putMention, changeName, changeContent } = this;
    return <MentionEditForm
      oldMention={oldMention}
      mentionRequest={mentionRequest}
      putMention={putMention}
      changeName={changeName}
      changeContent={changeContent} />
  }

  private putMention = () => {
    this.props.dispatcher.putMention(this.props.oldMention.id, this.state.mentionRequest);
    this.props.finishEditing();
  }

  private changeName = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({
      mentionRequest: {
        ...this.state.mentionRequest,
        name: e.target.value,
      }
    })
  }

  private changeContent = (_: React.FormEvent<HTMLTextAreaElement>, data: TextAreaProps) => {
    this.setState({
      mentionRequest: {
        ...this.state.mentionRequest,
        content: data.value as string,
      }
    })
  }
}

const mapStateToProps = ({ mentions }: IRootState) => ({
  rejected: mentions.editForm.get("rejected")
})

const mapDispatchToProps = (dispatch: Dispatch<editFormModule.Action>) => ({
  dispatcher: bindActionCreators(editFormModule, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(MentionEditFormContainer);