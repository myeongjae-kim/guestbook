import * as mentionAPI from 'main/api/mentions';
import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import MentionTable from 'main/ui/component/organisms/MentionTable';
import * as React from 'react';

export interface IState {
  mentions: IMentionResponse[]
  pending: boolean
  rejected: boolean
}

class MentionTableContainer extends React.Component<{}, IState> {
  public state = {
    mentions: [],
    pending: false,
    rejected: false
  }

  public async componentDidMount() {
    this.beforeSendingRequest();
    this.afterReceivingResponse(await mentionAPI.getList());
  }

  public render() {
    const { mentions, pending, rejected } = this.state;
    return <div style={{ opacity: pending ? 0.5 : 'initial' }}>
      {rejected ? "서버와 연결할 수 없습니다." : <MentionTable mentions={mentions} />}
    </div>
  }

  private beforeSendingRequest = () => this.setState({ pending: true, rejected: false })

  private afterReceivingResponse = (mentions?: IMentionResponse[]) => {
    if (mentions) {
      this.setState({ mentions, pending: false, rejected: false })
      return;
    }
    this.setState({ pending: false, rejected: true })
  }
}

export default MentionTableContainer;