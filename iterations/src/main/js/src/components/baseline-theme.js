import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import {teal, red, indigo} from "@material-ui/core/colors";

const theme = createMuiTheme({
    palette: {
        primary: {
            light: teal[200],
            main: teal[400],
            dark: teal[800],
            contrastText: '#fff',
        },
        secondary: {
            light: indigo[200],
            main: indigo[500],
            dark: indigo[900],
            contrastText: '#fff',
        },
    },
});

export default theme;