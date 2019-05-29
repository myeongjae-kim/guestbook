import IMentionRequest from 'main/api/mentions/dto/IMentionRequest';
import MentionAddForm from 'main/ui/component/MentionAddForm';
import { IRootState } from 'main/ui/modules';
import * as formModule from 'main/ui/modules/mentions/add-form';
import * as React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators, Dispatch } from 'redux';

interface IProps {
  mentionRequest: IMentionRequest
  pending: boolean
  rejected: boolean
  dispatcher: typeof formModule
}

class MentionAddFormContainer extends React.Component<IProps> {
  public render() {
    const { pending, mentionRequest } = this.props;
    const { postMention, changeName, changeContent } = this;
    return <div style={{ opacity: pending ? 0.5 : 'initial' }}>
      <MentionAddForm
        mentionRequest={mentionRequest}
        postMention={postMention}
        changeName={changeName}
        changeContent={changeContent} />
    </div>
  }

  private postMention = () => {
    this.props.dispatcher.postMention(this.props.mentionRequest);
  }

  private changeName = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.props.dispatcher.changeName(e.target.value)
  }

  private changeContent = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.props.dispatcher.changeContent(e.target.value)
  }
}

const mapStateToProps = ({ mentions }: IRootState) => ({
  mentionRequest: mentions.addForm.get("mentionRequest"),
  pending: mentions.addForm.get("pending"),
  rejected: mentions.addForm.get("rejected")
})

const mapDispatchToProps = (dispatch: Dispatch<formModule.Action>) => ({
  dispatcher: bindActionCreators(formModule, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(MentionAddFormContainer);