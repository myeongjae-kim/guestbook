import * as mentionAPI from 'main/api/mentions';
import IMentionResponse from 'main/api/mentions/dto/IMentionResponse';
import MentionTable from 'main/ui/component/organisms/MentionTable';
import * as React from 'react';

export interface IState {
  mention: IMentionResponse
}

class MentionTableContainer extends React.Component<{}, IState> {
  public state = {
    mention: {
      id: -1,
      name: "",
      content: "",
      createdAt: "1970-01-01T00:00:00.000Z"
    }
  }

  public async componentDidMount() {
    this.setState({ mention: await mentionAPI.get(1) })
  }

  public render() {
    return <MentionTable mentions={[this.state.mention]} />;
  }
}

export default MentionTableContainer;