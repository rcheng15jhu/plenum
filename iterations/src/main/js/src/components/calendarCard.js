//mark for deletion?
import React from 'react';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import EditIcon from '@material-ui/icons/Edit';

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  details: {
    display: 'flex',
    flexDirection: 'column',
  },
  content: {
    flex: '1 0 auto',
  },
  cover: {
    width: 151,
  },
  controls: {
    display: 'flex',
    alignItems: 'center',
    paddingLeft: theme.spacing(1),
    paddingBottom: theme.spacing(1),
  },
  editIcon: {
    height: 38,
    width: 38,
  },
}));

export default function CalendarCard(props) {
  const classes = useStyles();
  const theme = useTheme();

  return (
    <Card className={classes.root}>
      <div className={classes.details}>
        <CardContent className={classes.content}>
          <Typography component="h5" variant="h5">
            Calendar Title
          </Typography>
          <Typography variant="subtitle1" color="textSecondary">
            description
          </Typography>
        </CardContent>
        <div className={classes.controls}>
          <IconButton aria-label="edit">
            <EditIcon className={classes.editIcon} />
          </IconButton>
        </div>
      </div>
      <Card>{props.calendar}</Card>
    </Card>
  );
}
//mark for deletion?
