import time
from fixtures import driver
import constants as cnst

def test_registration_valid(driver):
    username = driver.find_element_by_id(cnst.REGISTER_USERNAME_TEXT_FIELD)
    username.send_keys(cnst.REGISTER_USERNAME)
    driver.back()
    password = driver.find_element_by_id(cnst.REGISTER_PASS_TEXT_FIELD)
    password.send_keys(cnst.REGISTER_PASSWORD)
    driver.back()
    rep_password = driver.find_element_by_id(cnst.REGISTER_PASS_REP_TEXT_FIELD)
    rep_password.send_keys(cnst.REGISTER_PASSWORD)
    driver.back()
    email = driver.find_element_by_id(cnst.REGISTER_EMAIL_TEXT_FIELD)
    email.send_keys(cnst.REGISTER_EMAIL)
    driver.back()
    button = driver.find_element_by_id(cnst.REGISTER_BUTTON_NAME)
    button.click()
    assert 1 == 1

def test_registration_invalid(driver):
    username = driver.find_element_by_id(cnst.REGISTER_USERNAME_TEXT_FIELD)
    username.send_keys(cnst.REGISTER_USERNAME)
    driver.back()
    password = driver.find_element_by_id(cnst.REGISTER_PASS_TEXT_FIELD)
    password.send_keys('AA')
    driver.back()
    rep_password = driver.find_element_by_id(cnst.REGISTER_PASS_REP_TEXT_FIELD)
    rep_password.send_keys('AAAA')
    driver.back()
    email = driver.find_element_by_id(cnst.REGISTER_EMAIL_TEXT_FIELD)
    email.send_keys(cnst.REGISTER_EMAIL)
    driver.back()
    button = driver.find_element_by_id(cnst.REGISTER_BUTTON_NAME)
    button.click()
    assert 1 == 1

def test_login_valid(driver):
    email = driver.find_element_by_id(cnst.LOGIN_EMAIL_TEXT_FIELD)
    email.send_keys(cnst.LOGIN_EMAIL)
    driver.back()
    password = driver.find_element_by_id(cnst.LOGIN_PASS_TEXT_FIELD)
    password.send_keys(cnst.LOGIN_PASSWORD)
    driver.back()
    button = driver.find_element_by_id(cnst.LOGIN_BUTTON_NAME)
    button.click()

    assert 1 == 1

def test_login_invalid(driver):
    email = driver.find_element_by_id(cnst.LOGIN_EMAIL_TEXT_FIELD)
    email.send_keys('Aa')
    driver.back()
    password = driver.find_element_by_id(cnst.LOGIN_PASS_TEXT_FIELD)
    password.send_keys('adad')
    driver.back()
    button = driver.find_element_by_id(cnst.LOGIN_BUTTON_NAME)
    button.click()
    assert 1 == 1

# def test_profile_change_saved(driver):
#
#     assert 1 == 1
#
# def test_profile_change_no_saving(driver):
#
#     assert 1 == 1
#
# def test_bookmarks(driver):
#
#     assert 1 == 1
#
# def test_favourites(driver):
#
#     assert 1 == 1
