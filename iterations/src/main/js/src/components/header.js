import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import {AccountCircle} from "@material-ui/icons";
import theme from "./baseline-theme";
import {ThemeProvider} from "@material-ui/styles";
import HomeRoundedIcon from '@material-ui/icons/HomeRounded';
import cookieManager from "../services/cookie-manager";

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        width: '100%',
        overflow: "hidden",
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
}));

export default function ButtonAppBar() {

    const classes = useStyles();

    const [anchorEl, setAnchorEl] = React.useState(null);

    const isMenuOpen = Boolean(anchorEl);

    const handleProfileMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleMenuCloseProfile = () => {
        window.location.assign('/profile');
        handleMenuClose();
    };

    const handleMenuCloseLogout = () => {
        document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        window.location.assign('/');
        handleMenuClose();
    };

    const menuId = 'primary-search-account-menu';
    const renderMenu = (
        <Menu
            anchorEl={anchorEl}
            anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            id={menuId}
            keepMounted
            transformOrigin={{ vertical: 'top', horizontal: 'right' }}
            open={isMenuOpen}
            onClose={handleMenuClose}
        >
            <MenuItem href='/profile' onClick={handleMenuCloseProfile}>Profile</MenuItem>
            <MenuItem onClick={handleMenuCloseLogout}>Logout</MenuItem>
        </Menu>
    );

    return (
        <ThemeProvider theme={theme}>
        <div className={classes.root}>
            <AppBar position="static" style={{"marginBottom": "30px"}}>
                <Toolbar>
                    <Typography variant="h6" className={classes.title}>
                        <Button variant='text' size='large' href="/list-calendar" color="inherit">
                            <HomeRoundedIcon style={{'marginRight': '5px'}} />
                            Plenum
                        </Button>
                    </Typography>
                    <Button href="/list-calendar" color="inherit">Calendars</Button>
                    <Button href="/list-events" color="inherit">Events</Button>
                    <IconButton
                        edge="end"
                        aria-label="account of current user"
                        aria-controls={menuId}
                        aria-haspopup="true"
                        onClick={handleProfileMenuOpen}
                        color="inherit"
                    >
                        <AccountCircle />
                    </IconButton>
                </Toolbar>
            </AppBar>
            {renderMenu}
        </div>
        </ThemeProvider>
    );
}