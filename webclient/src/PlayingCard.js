import React from 'react';

import {DragSource, DropTarget} from 'react-dnd';

import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';

import {ItemTypes, FieldIDs} from './Constants.js';
import {PlayCard, ActivateCard, SelectCard} from './Game.js';
import './PlayingCard.css';

const cardSource = {
  beginDrag(props) {
    return {};
  },

  canDrag(props) {
    return props.playable;
  },

  endDrag(props, monitor) {
    if (!monitor.didDrop()) {
      return;
    }

    const targetProps = monitor.getDropResult();
    if (
      props.playable &&
      (targetProps.targetType === ItemTypes.FIELD &&
        targetProps.id === FieldIDs.MY_FIELD)
    ) {
      PlayCard(props.id);
    }
  },
};

const cardTarget = {
  drop(props, monitor) {
    if (monitor.didDrop()) {
      return;
    }

    return {targetType: ItemTypes.CARD, id: props.id};
  },
};

export class PlayingCard extends React.Component {
  render() {
    const {
      id,
      name,
      description,
      activatable,
      playable,
      isOver,
      canDrop,
      isDragging,
      connectDragSource,
      connectDropTarget,
      selectable,
    } = this.props;

    var opacity = 1,
      border = 'none';
    if (isDragging) {
      opacity = 0.5;
      border = '5px blue solid';
    } else if (canDrop && isOver) {
      border = '5px red solid';
    } else if (selectable) {
      border = '5px green solid';
    }

    let clickHandler = undefined;
    if (selectable) {
      clickHandler = event => {
        SelectCard(id);
      };
    } else if (activatable) {
      clickHandler = event => {
        ActivateCard(id);
      };
    }

    return connectDropTarget(
      connectDragSource(
        <div>
          <Card
            style={{
              opacity: opacity,
              border: border,
            }}
            onClick={clickHandler}>
            <CardHeader title={name} />
            <CardContent>{description}</CardContent>
          </Card>
        </div>,
      ),
    );
  }
}

PlayingCard = DragSource(ItemTypes.CARD, cardSource, (connect, monitor) => ({
  connectDragSource: connect.dragSource(),
  isDragging: monitor.isDragging(),
}))(PlayingCard);

PlayingCard = DropTarget(ItemTypes.CARD, cardTarget, (connect, monitor) => ({
  connectDropTarget: connect.dropTarget(),
  isOver: monitor.isOver(),
  canDrop: monitor.canDrop(),
}))(PlayingCard);

export default PlayingCard;
